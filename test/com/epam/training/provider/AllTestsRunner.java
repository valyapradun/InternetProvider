package com.epam.training.provider;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class AllTestsRunner extends BlockJUnit4ClassRunner {
	private AllTestsRunListener runListener;

	public AllTestsRunner(Class<AllTestsRunListener> klass) throws InitializationError {
		super(klass);
		runListener = new AllTestsRunListener();
	}

	public void run(RunNotifier notifier) {
		notifier.addListener(runListener);
		super.run(notifier);
	}
}
