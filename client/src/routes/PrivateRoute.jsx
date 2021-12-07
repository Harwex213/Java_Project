import React from "react";
import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
import { useAuthContext } from "../app/AuthContext";
import routePaths from "../common/constants/routePaths";

const PrivateRoute = ({ children, roles, navigateTo }) => {
    const { isAuth, user } = useAuthContext();
    const isAllowed = isAuth && roles.map((role) => role.toUpperCase()).includes(user.role);

    return isAllowed ? children : <Navigate to={navigateTo ? navigateTo : routePaths.notFound} replace />;
};

PrivateRoute.propTypes = {
    children: PropTypes.element,
    roles: PropTypes.array,
    navigateTo: PropTypes.string,
};

export default PrivateRoute;
