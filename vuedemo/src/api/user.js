import request from "@/utils/request.js";

export const login = (data)=>{
    return request({
        url:"/login",
        method:"post",
        data:data
    });
};

export const refreshToken = (refreshToken) => {
    return request({
        url: "/refreshToken", // mock接口
        method: "get",
        params: {
            refreshToken,
        },
    });
};

export const getAllUsers = ()=>{
    return request({
        url:"/users",
        method:"get"
    });
}