import React from "react";
import { Link, Route, Routes } from "react-router-dom";
import Movies from "../../components/dashboard/Movies";
import Cinemas from "../../components/dashboard/Cinemas";
import Sessions from "../../components/dashboard/Sessions";
import { useAuthContext } from "../../app/AuthContext";

const AdminMain = () => {
    const { user } = useAuthContext();

    return (
        <div>
            <div className="menu">
                <Link className="menu__item" to="movies">
                    Movies
                </Link>
                <Link className="menu__item" to="cinemas">
                    Cinemas
                </Link>
            </div>
            <Routes>
                <Route path="" element={<div>Hello, {user.username}</div>} />
                <Route path="movies" element={<Movies />} />
                <Route path="cinemas/*" element={<Cinemas />} />
            </Routes>
        </div>
    );
};

export default AdminMain;
