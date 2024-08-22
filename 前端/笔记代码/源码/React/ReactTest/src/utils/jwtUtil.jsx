


export const getJwt=()=>{
    return localStorage.getItem('jwt');
};

export const setJwt=(jwt)=>{
    localStorage.setItem('jwt',jwt);
};