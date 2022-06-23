import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../layout/Layout.vue'
import LoginView from "@/views/LoginView";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: "/user",      //重定向

        children: [
            {
                path: 'user',
                name: 'UserView',
                component: () => import("@/views/UserView"),
            },
            {
                path: 'goods',
                name: 'Goods',
                component: () => import("@/views/GoodsView"),
            }, {
                path: 'order',
                name: 'OrderView',
                component: () => import("@/views/OrderView"),
            },

        ]
    },
    {
        path: '/login',
        name: 'LoginView',
        component: () => import("@/views/LoginView")
    },
    // {
    //     path: '/user',
    //     name: 'UserView',
    //     component: () => import("@/views/UserView")
    // },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
