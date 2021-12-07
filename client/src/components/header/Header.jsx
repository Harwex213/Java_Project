import React from "react";
import { Link } from "react-router-dom";
import { useAuthContext } from "../../app/AuthContext";
import routePaths from "../../common/constants/routePaths";
import userRoles from "../../common/constants/userRoles";
import "../header/styles.css";
import authApi from "../../api/auth";

const GuestMenu = () => {
    return (
        <>
            <Link className="menu__item" to={routePaths.login}>
                Login
            </Link>
            <Link className="menu__item" to={routePaths.register}>
                Register
            </Link>
        </>
    );
};

const UserMenu = () => {
    return (
        <>
            <Link className="menu__item" to={routePaths.myTickets}>
                My tickets
            </Link>
            <button className="menu__item" onClick={() => authApi.logout()}>
                Log out
            </button>
        </>
    );
};

const AdminMenu = () => {
    return <div>admin!</div>;
};

const Header = () => {
    const { isAuth, user } = useAuthContext();

    let menu = <GuestMenu />;
    if (isAuth && user.role === userRoles.admin) {
        menu = <AdminMenu />;
    }
    if (isAuth && user.role === userRoles.user) {
        menu = <UserMenu />;
    }

    return (
        <div className="menu">
            <Link className="menu__item" to={routePaths.main}>
                Home
            </Link>
            {menu}
        </div>
    );
};

export default Header;
