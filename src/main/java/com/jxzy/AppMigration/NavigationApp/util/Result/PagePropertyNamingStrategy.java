package com.jxzy.AppMigration.NavigationApp.util.Result;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;

public class PagePropertyNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    private static final long serialVersionUID = -5989641972134253152L;
    protected static final String PAGE_CURRENT_ORI = "current";
    protected static final String PAGE_SIZE_ORI = "size";
    protected static final String PAGE_TOTAL_ORI = "total";
    protected static final String PAGE_RECORDS_ORI = "records";
    protected static final String PAGE_PAGES_ORI = "pages";
    @Value("${common.page.current:current}")
    private String current;
    @Value("${common.page.size:size}")
    private String size;
    @Value("${common.page.total:total}")
    private String total;
    @Value("${common.page.records:records}")
    private String records;
    @Value("${common.page.pages:pages}")
    private String pages;

    public PagePropertyNamingStrategy() {
    }

    public String translate(String ori) {
        byte var3 = -1;
        switch(ori.hashCode()) {
            case 3530753:
                if (ori.equals("size")) {
                    var3 = 1;
                }
                break;
            case 106426308:
                if (ori.equals("pages")) {
                    var3 = 4;
                }
                break;
            case 110549828:
                if (ori.equals("total")) {
                    var3 = 2;
                }
                break;
            case 1082596930:
                if (ori.equals("records")) {
                    var3 = 3;
                }
                break;
            case 1126940025:
                if (ori.equals("current")) {
                    var3 = 0;
                }
        }

        switch(var3) {
            case 0:
                return this.current;
            case 1:
                return this.size;
            case 2:
                return this.total;
            case 3:
                return this.records;
            case 4:
                return this.pages;
            default:
                return ori;
        }
    }
}
