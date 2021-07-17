package com.fujfu.frontend.permission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fujfu.frontend.constant.ResponseConstants;
import com.fujfu.frontend.permission.authentication.Authentication;
import com.fujfu.frontend.permission.authentication.AuthenticationHolder;
import com.fujfu.frontend.web.controller.mo.ResponseMO;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Beldon
 */
public class PermissionAdvice implements MethodInterceptor {

    private static final Map<String, List<String>> PERMISSION_DATA_MAP;

    static {
        try {
            PERMISSION_DATA_MAP = loadPermissions();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Optional<Authentication> authenticationOptional = AuthenticationHolder.get();
        if (authenticationOptional.isEmpty()) {
            return ResponseMO.response(ResponseConstants.RESPONSE_CODE_ANONYMOUS, "anonymous");
        }

        Authentication authentication = authenticationOptional.get();

        String mobile = authentication.getMobile();
        Permission permission = invocation.getMethod().getAnnotation(Permission.class);
        if (hasPermission(mobile, permission.permissions())) {
            return invocation.proceed();
        }
        return ResponseMO.response(ResponseConstants.RESPONSE_CODE_ACCESS_DENIED, "无权限");
    }

    /**
     * 可以通过接口去查询相应的权限
     *
     * @param mobile
     * @param permissions
     * @return
     */
    private boolean hasPermission(String mobile, String[] permissions) {
        if (permissions.length == 0) {
            return true;
        }
        if (!PERMISSION_DATA_MAP.containsKey(mobile)) {
            return false;
        }
        final List<String> hasPermissions = PERMISSION_DATA_MAP.get(mobile);
        for (String permission : permissions) {
            if (hasPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载 permissions
     *
     * @return
     */
    private static Map<String, List<String>> loadPermissions() throws IOException {
        Map<String, List<String>> data = new HashMap<>();
        try (
                final InputStream is = Files.newInputStream(Paths.get("./permissions.json"))
        ) {
            final String permissionsStr = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            final JSONArray arr = JSON.parseArray(permissionsStr);
            for (int i = 0; i < arr.size(); i++) {
                final JSONObject jsonObject = arr.getJSONObject(i);
                final JSONArray permissionArr = jsonObject.getJSONArray("permissions");
                final List<String> permissions = permissionArr.toJavaList(String.class);
                data.put(jsonObject.getString("mobile"), permissions);
            }
        }
        return data;
    }

}
