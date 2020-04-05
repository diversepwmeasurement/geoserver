/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * (c) 2001 - 2013 OpenPlans
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.admin;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.util.tester.FormTester;
import org.geoserver.config.GeoServer;
import org.geoserver.config.GeoServerInfo;
import org.geoserver.web.GeoServerHomePage;
import org.geoserver.web.GeoServerWicketTestSupport;
import org.junit.Before;
import org.junit.Test;

public class GlobalSettingsPageTest extends GeoServerWicketTestSupport {

    private GeoServer gs;

    @Before
    public void reset() {
        gs = getGeoServerApplication().getGeoServer();
        GeoServerInfo info = gs.getGlobal();
        info.getSettings().setVerbose(false);
        gs.save(info);
    }

    @Test
    public void testValues() {
        GeoServerInfo info = gs.getGlobal();

        login();
        tester.startPage(GlobalSettingsPage.class);
        tester.assertComponent("form:verbose", CheckBox.class);
        tester.assertModelValue("form:verbose", info.getSettings().isVerbose());
        tester.assertComponent("form:showCreatedTimeCols", CheckBox.class);
        tester.assertModelValue(
                "form:showCreatedTimeCols",
                info.getSettings().isShowCreatedTimeColumnsInAdminList());
        tester.assertComponent("form:showModifiedTimeCols", CheckBox.class);
        tester.assertModelValue(
                "form:showModifiedTimeCols",
                info.getSettings().isShowModifiedTimeColumnsInAdminList());
    }

    @Test
    public void testSave() {
        GeoServerInfo info = gs.getGlobal();

        login();
        tester.startPage(GlobalSettingsPage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("verbose", true);
        ft.submit("submit");
        
        tester.assertRenderedPage(GeoServerHomePage.class);
        assertTrue(gs.getSettings().isVerbose());
    }

    @Test
    public void testApply() {
        GeoServerInfo info = gs.getGlobal();

        login();
        tester.startPage(GlobalSettingsPage.class);
        FormTester ft = tester.newFormTester("form");
        ft.setValue("verbose", true);
        ft.submit("apply");

        tester.assertRenderedPage(GlobalSettingsPage.class);
        assertTrue(gs.getSettings().isVerbose());
    }
}
