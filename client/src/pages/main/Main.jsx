import React from "react";
import { useAuthContext } from "../../app/AuthContext";
import DefaultMain from "./DefaultMain";
import AdminMain from "./AdminMain";
import userRoles from "../../common/constants/userRoles";
import "./styles.css";

const Main = () => {
    const { isAuth, user } = useAuthContext();
    let main = <DefaultMain />;

    if (isAuth && user.role === userRoles.admin) {
        main = <AdminMain />;
    }
    return <div className="wrapper">{main}</div>;
};

export default Main;
