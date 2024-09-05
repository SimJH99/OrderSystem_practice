import { createRouter, createWebHistory } from "vue-router";
import LoginComponent from '@/views/LoginComponent.vue';
import ItemList from '@/views/ItemList.vue';
// export default인 경우에는 {} 필요없고, 여러 개 요소가 있을 경우에는 {} 필요
import { memberRoutes } from "./memberRoute.js";
import { orderRoutes } from "./orderRouter.js";
import { itemRoutes } from "./itemRouter.js";

const routes = [
    {
        //url경로 지정
        path: '/',
        // 이 라우터의 이름
        name: 'HOME',
        component: ItemList,
    },
    {
        path: '/login',
        name: 'LoginComponent',
        component: LoginComponent,
    },

    // ...은 스프레드 연산자로 불리고, 주로 배열요소를 다른 배열요소에 합할 때 사용
    ...memberRoutes,
    ...orderRoutes,
    ...itemRoutes,
];

const router = createRouter({
    // vue router는 내부적으로 두 가지 방식의 히스토리 관리를 제공
    // 1) createWebHistory, createHashHistory
    history: createWebHistory(),
    routes
});

export default router;