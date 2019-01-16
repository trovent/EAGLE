import { helpers } from "../../helpers/helpers";

const user = helpers.getUser();
const token = helpers.getToken();

const initState = token ? {
        loggingIn : false,
        loggedIn: true,
        user
    } : {};


const authReducer = (state = initState, action ) => {
    switch(action.type){
        case 'LOGIN_REQUEST':
            return {
                loggingIn : true,
                loggedIn: false,
                user
            };
        case 'LOGIN_SUCCESS':
            return {
                loggingIn : false,
                loggedIn: true,
                user
            };
        case 'LOGIN_ERROR':
            return {
                loggingIn : false,
                loggedIn: false,
                user: ""
            };
        case 'LOGOUT_SUCCESS':
            return {
                loggingIn : false,
                loggedIn: false,
                user: ""
            };
        default:
            return state;
    }

};

export default authReducer;