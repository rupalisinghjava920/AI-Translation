package com.ai.translation.unit;

public class Constant {

        // ========== USER / AUTH =========
        public static final String USER_REGISTER_SUCCESS = "User registered successfully";
        public static final String USER_ALREADY_EXISTS = "User already exists with this email";
        public static final String USER_LOGIN_SUCCESS = "Login successful";
        public static final String INVALID_CREDENTIALS = "Invalid username or password";

        // ========== VALIDATION =========
        public static final String REQUIRED_FIELDS = "Required fields are missing";
        public static final String INVALID_EMAIL = "Please enter a valid email address";

        // ========== TRANSLATION =========
        public static final String TRANSLATION_SUCCESS = "Text translated successfully";
        public static final String TRANSLATION_FAILED = "Translation failed. Please try again";
        public static final String INVALID_LANGUAGE = "Please select valid source and target languages";
        public static final String SAME_LANGUAGE = "Source and target language cannot be the same";

        // ========== RATE LIMIT =========
        public static final String TOO_MANY_REQUESTS = "Too many requests. Please try again later";

        // ======== HISTORY ======
        public static final String HISTORY_FETCH_SUCCESS = "Translation history fetched successfully";
        public static final String HISTORY_EMPTY = "No translation history found";

        // ======== GENERAL ERRORS ========
        public static final String SERVER_ERROR = "Something went wrong. Please try again later";

}
