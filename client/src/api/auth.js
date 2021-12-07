import { authApi as auth, privateApi } from "./api";
import { killToken } from "../common/jwt/jwt";
import queryClient from "../app/queryClient";

const login = ({ username, password }) =>
    auth
        .post("login", {
            json: { username, password },
        })
        .json();

const register = ({ username, email, password, repeatedPassword }) =>
    auth
        .post("register", {
            json: { username, email, password, repeatedPassword },
        })
        .json();

const getMe = () =>
    privateApi
        .extend({
            prefixUrl: "http://localhost:8080/auth",
        })
        .get("")
        .json();

const logout = () => {
    killToken();
    queryClient.invalidateQueries("user");
};

const authApi = {
    login,
    register,
    logout,
    getMe,
};

export default authApi;
