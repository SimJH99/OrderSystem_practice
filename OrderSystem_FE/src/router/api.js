import axios from "axios";

// const token = localStorage.getItem('token');
// const headers = token ? {Authorization : `Bearer ${token}`} : {};
 
const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
  });


function getItems(params) {
    return instance.post('/items', params);
}

export{ getItems }




// function makeUrl (apiName) {
//     let url = BASE_URL;
//     if (url.slice(-1) != '/') url += '/' // 끝에 / 를 추가

//     // 시작 부분에 / 를 제거
//     if (apiName.slice(0, 1) == '/') url += apiName.slice(1)
//     else url += apiName

//     return url
// }

// export {
//     getItems
// }