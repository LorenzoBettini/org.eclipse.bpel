/******************************************************************************
 * Copyright (c) 2011, EBM WebSourcing
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     EBM WebSourcing - initial API and implementation
 *******************************************************************************/

package org.eclipse.bpel.ui.tests.utils;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

/**
 *
 * @author Vincent Zurczak - EBM WebSourcing
 */
public class JobHelper {

	private final IJobChangeListener jobChangeListener;
	private final List<Job> newJobs = new ArrayList<Job> ();
	private boolean started;


	/**
	 * Constructor.
	 */
	public JobHelper() {

		this.jobChangeListener = new IJobChangeListener() {

			@Override
			public void sleeping( IJobChangeEvent event ) {
				// nothing
			}

			@Override
			public void scheduled( IJobChangeEvent event ) {
				JobHelper.this.newJobs.add( event.getJob());
			}

			@Override
			public void running( IJobChangeEvent event ) {
				// nothing
			}

			@Override
			public void done( IJobChangeEvent event ) {
				// nothing
			}

			@Override
			public void awake( IJobChangeEvent event ) {
				// nothing
			}

			@Override
			public void aboutToRun( IJobChangeEvent event ) {
				// nothing
			}
		};
	}


	/**
	 * Starts listening to the jobs.
	 */
	public void start() {
		if( ! this.started ) {
			this.started = true;
			Job.getJobManager().addJobChangeListener( this.jobChangeListener );
		}
	}


	/**
	 * Starts listening to the jobs.
	 */
	public void stop() {
		Job.getJobManager().removeJobChangeListener( this.jobChangeListener );
		this.started = false;
	}


	/**
	 * @param jobTrackerAction
	 * @return
	 * @throws Exception
	 */
	public List<Job> listNewlyScheduledJobs( JobTrackerAction jobTrackerAction ) throws Exception {

		if( Job.getJobManager().isSuspended())
			throw new ConcurrentModificationException();

		Job.getJobManager().suspend();
		this.newJobs.clear();
		jobTrackerAction.executeJobTrigger();
		Job.getJobManager().resume();

		return this.newJobs;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		stop();
		super.finalize();
	}


	/**
	 * A class whose method will be executed to trigger new jobs scheduling.
	 */
	public abstract static class JobTrackerAction {
		public abstract void executeJobTrigger() throws Exception;
	}
}
