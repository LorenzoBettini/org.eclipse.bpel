/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.common.ui.palette;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.bpel.common.ui.CommonUIPlugin;
import org.eclipse.bpel.common.ui.details.IDetailsColors;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteEditPartFactory;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;


/**
 * Graphical editor to be integrated with a GEFDetailsEditor.
 */
public abstract class GraphicalEditorWithPalette extends GraphicalEditor {

	static final String EXTPT_PALETTE_ADDITIONS = "paletteAdditions"; //$NON-NLS-1$
	static final String ELEMENT_ADDITIONS = "additions"; //$NON-NLS-1$
	static final String ATT_CLASS = "class"; //$NON-NLS-1$
	static final String ATT_DEFAULT = "default"; //$NON-NLS-1$
	static final String ATT_CATEGORY = "category"; //$NON-NLS-1$
	static final String ATT_TARGET_EDITOR = "targetEditor"; //$NON-NLS-1$
	
	private PaletteViewer paletteViewer;
	private PaletteRoot paletteRoot;

	public GraphicalEditorWithPalette() {
		super();
	}

	public void dispose() {
		super.dispose();
		paletteViewer = null;
		paletteRoot = null;
	}

	/**
	 * Creates the palette and graphical viewers.
	 */
	public void createPartControl(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		main.setLayout(layout);

		Composite palette = new Composite(main, SWT.NONE);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		palette.setLayoutData(data);
		palette.setLayout(new FillLayout());
		createPaletteViewer(palette);

		Composite editor = new Composite(main, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(palette);
		data.right = new FormAttachment(100, 0);
		editor.setLayoutData(data);
		editor.setLayout(new FillLayout());
		createGraphicalViewer(editor);

		main.pack(true); // adjust widget sizes
		
		Control tabs[] = {editor, palette};
		main.setTabList(tabs);
	}

	protected void createPaletteViewer(final Composite parent) {
		paletteViewer = new PaletteViewer();
		paletteViewer.createControl(parent);
		final FigureCanvas canvas = (FigureCanvas) paletteViewer.getControl();
		canvas.setScrollBarVisibility(FigureCanvas.NEVER);
		PaletteEditPartFactory factory = new PaletteEditPartFactory();
		paletteViewer.setEditPartFactory(factory);
		ColorRegistry registry = CommonUIPlugin.getDefault().getColorRegistry();
		Color background = registry.get(IDetailsColors.COLOR_CANVAS);
		canvas.setBackground(background);
		getEditDomain().setPaletteViewer(paletteViewer);
		getEditDomain().setPaletteRoot(getPaletteRoot());
		// if the palette canvas resizes we need to modify the layout accordingly
		canvas.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				FormData data = (FormData) parent.getLayoutData();
				data.width = canvas.getSize().x;
				parent.getParent().layout(true);
			}
		});
	}

	/**
	 * The implementation in new versions of GraphicalEditor assumes that 'this' will be
	 * the top-level editor.  For us, the assumption is most likely wrong--a DetailsEditor
	 * is probably the top-level editor...  
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		updateActions(getSelectionActions());
	}

	public PaletteViewer getPaletteViewer() {
		return paletteViewer;
	}

	protected PaletteRoot getPaletteRoot() {
		if (paletteRoot == null) {
			paletteRoot = createPaletteRoot();
			// Create extension items
			HashMap categoryMap = new HashMap();
			Iterator it = paletteRoot.getChildren().iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof PaletteCategory) {
					categoryMap.put(((PaletteCategory)o).getLabel(), o);
				}
			}
			createPaletteAdditions(categoryMap);
		}
		return paletteRoot;
	}

	protected String getPaletteAdditionsContributorId() {
		return getEditorSite().getId();
	}
	
	protected void createPaletteAdditions(HashMap categoryMap) {
		// Get the id of this editor
		String id = getPaletteAdditionsContributorId();
		
		// Read the extensions
		IConfigurationElement[] extensions = getConfigurationElements(EXTPT_PALETTE_ADDITIONS);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement additions = extensions[i];
			if (additions.getName().equals(ELEMENT_ADDITIONS)) {
				String editorId = additions.getAttribute(ATT_TARGET_EDITOR);
				if (id.equals(editorId)) {
					IConfigurationElement[] children = additions.getChildren();
					for (int j = 0; j < children.length; j++) {
						IConfigurationElement addition = children[j];
						String def = addition.getAttribute(ATT_DEFAULT);
						String categoryName = addition.getAttribute(ATT_CATEGORY);
						
						if (categoryName == null) {
							// If category is null, add the tool entry to the palette root
							try {
								PaletteEntry entry = (PaletteEntry)addition.createExecutableExtension(ATT_CLASS);						
								paletteRoot.add(entry);
							} catch (CoreException e) {
								CommonUIPlugin.getDefault().getLog().log(e.getStatus());
							}
						} else {
							// If category is non-null, create the category if it does not exist and add the 
							// tool entry to the category. If the tool entry is default, set the default of
							// the category.
							try {
								PaletteCategory category = (PaletteCategory)categoryMap.get(categoryName);
								if (category == null) {
									category = new PaletteCategory(""); //$NON-NLS-1$
									categoryMap.put(categoryName, category);
									paletteRoot.add(category);
								}
								ToolEntry entry = (ToolEntry)addition.createExecutableExtension(ATT_CLASS);						
								category.add(entry);
								if (def != null && def.equals("true")) { //$NON-NLS-1$
									category.setDefaultTool(entry);
								}
							} catch (CoreException e) {
								CommonUIPlugin.getDefault().getLog().log(e.getStatus());
							}
						}
					}
				}
			}
		}		
	}
	
	/**
	 * Returns a PaletteRoot populated with tools.
	 * <p>
	 * The allowed model objects are PaletteCategory and ToolEntry.
	 */
	protected abstract PaletteRoot createPaletteRoot();

	private IConfigurationElement[] getConfigurationElements(String extensionPointId) {
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(CommonUIPlugin.PLUGIN_ID, extensionPointId);
		if (extensionPoint == null) {
			return new IConfigurationElement[0];
		}
		return extensionPoint.getConfigurationElements();
	}
}
