import React from "react";
import { createContext, useContext } from "react";
import PropTypes from "prop-types";
import { useQuery } from "react-query";
import authApi from "../api/auth";

const defaultValue = {
    isAuth: false,
    user: null,
};

export const AuthContext = createContext(defaultValue);

export const useAuthContext = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const { isLoading, isSuccess, data } = useQuery("user", authApi.getMe, {
        retry: 0,
    });
    if (isLoading) {
        return <></>;
    }

    const value = isSuccess ? { isAuth: true, user: data } : defaultValue;
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

AuthProvider.propTypes = {
    children: PropTypes.element,
};
