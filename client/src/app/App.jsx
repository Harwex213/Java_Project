import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import PrivateRoute from "../routes/PrivateRoute";
import GuestRoute from "../routes/GuestRoute";
import Header from "../components/header/Header";
import Main from "../pages/main/Main";
import Login from "../pages/login/Login";
import Register from "../pages/register/Register";
import MyTickets from "../pages/myTickets/MyTickets";

import routePaths from "../common/constants/routePaths";
import userRoles from "../common/constants/userRoles";

function App() {
    return (
        <>
            <Header />
            <Routes>
                <Route path={`${routePaths.empty}`} element={<Navigate to={routePaths.main} replace />} />
                <Route path={`${routePaths.main}/*`} element={<Main />} />
                <Route
                    path={routePaths.login}
                    element={
                        <GuestRoute navigateTo={routePaths.main} isGuestRoute>
                            <Login />
                        </GuestRoute>
                    }
                />
                <Route
                    path={routePaths.register}
                    element={
                        <GuestRoute navigateTo={routePaths.main} isGuestRoute>
                            <Register />
                        </GuestRoute>
                    }
                />
                <Route
                    path={routePaths.myTickets}
                    element={
                        <PrivateRoute roles={[userRoles.user]} navigateTo={routePaths.login}>
                            <MyTickets />
                        </PrivateRoute>
                    }
                />
                <Route path={routePaths.notFound} element={<div>Error 404</div>} />
                <Route path={routePaths.any} element={<div>Error 404</div>} />
            </Routes>
        </>
    );
}

export default App;
