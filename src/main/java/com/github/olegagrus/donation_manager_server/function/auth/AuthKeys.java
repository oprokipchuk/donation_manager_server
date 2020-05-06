package com.github.olegagrus.donation_manager_server.function.auth;

public interface AuthKeys {

    String GET_TOKEN_PARAM_GRANT_TYPE_NAME = "grant_type";

    String GET_TOKEN_PARAM_CLIENT_ID_NAME = "client_id";

    String GET_TOKEN_PARAM_CLIENT_SECRET_NAME = "client_secret";

    String GET_TOKEN_PARAM_REDIRECT_URI_NAME = "redirect_uri";

    String GET_TOKEN_PARAM_CODE_NAME = "code";

    String GET_TOKEN_PARAM_REFRESH_TOKEN_NAME = "refresh_token";

    String CLOSE_WINDOW_TEMPLATE_STRING = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<script>window.close();</script>\n" +
            "</body>\n" +
            "</html>";

}
