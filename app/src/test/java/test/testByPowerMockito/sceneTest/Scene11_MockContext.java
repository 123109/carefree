package test.testByPowerMockito.sceneTest;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Locale;

import base.TestInit;

/**
 * 场景11：mock一个context
 * Created by cb on 2016/5/31.
 */
public class Scene11_MockContext extends TestInit{
    @Test
    public void testMockContext() {
        Context context = getContext();
        Locale locale = context.getResources().getConfiguration().locale;
        Assert.assertNotNull(locale);
    }
}
