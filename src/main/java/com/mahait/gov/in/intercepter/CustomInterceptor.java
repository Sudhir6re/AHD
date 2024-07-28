package com.mahait.gov.in.intercepter;

import org.hibernate.EmptyInterceptor;

public class CustomInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        // Suppress specific queries
        if (sql.contains("sub_menu_mst")) {
            return ""; // Suppress this SQL
        }
        return super.onPrepareStatement(sql);
    }
}