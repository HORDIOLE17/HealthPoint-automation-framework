package com.healthpoint.automation.retry;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            Constructor testConstructor,
            Method testMethod
    ) {

        if (testClass != null &&
                testClass.getPackageName()
                        .equals("com.healthpoint.automation.ui")) {

            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
