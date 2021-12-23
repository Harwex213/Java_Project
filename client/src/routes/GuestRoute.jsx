import React from "react";
import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
import { useAuthContext } from "../app/AuthContext";
import routePaths from "../common/constants/routePaths";

const GuestRoute = ({ children, navigateTo }) => {
    const { isAuth } = useAuthContext();
    const isAllowed = !isAuth;

    return isAllowed ? children : <Navigate to={navigateTo ? navigateTo : routePaths.notFound} replace />;
};

GuestRoute.propTypes = {
    children: PropTypes.element,
    navigateTo: PropTypes.string,
};

export default GuestRoute;
