package com.donalola.api.authentication.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenExtractor {

    String extract(HttpServletRequest request);

    boolean extractApplies(List<String> keys);

}
