
package com.server.service.service;

import org.springframework.stereotype.Component;


@Component("options")
public class Options {
    
    private boolean showSql = true;
    
    private boolean showParams = false;
    
    private int maxRows = 20000;
    
    private int timeOut = 300;
    
    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public boolean isShowParams() {
        return showParams;
    }

    public void setShowParams(boolean showParams) {
        this.showParams = showParams;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
