import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../layout/Manage.vue'
import LoginView from "@/views/ManageLoginView";
import Manage from "@/layout/Manage"
import FlowerMall from "@/layout/FlowerMall";
import InfoView from "@/views/InfoView";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'FlowerMall',
        component: FlowerMall,
        redirect: "/index",
        children: [
            {
                path: 'index',
                name: 'Index',
                component: () => import("@/views/Index"),
            },
            {
                path: '/login',
                name: 'LoginView',
                component: () => import("@/views/LoginView")
            },
            {
                path: '/register',
                name: 'RegisterView',
                component: () => import("@/views/RegisterView")
            },
            {
                path: 'info',
                name: 'Info',
                component: InfoView,
                redirect: "/info/infoBase",      //重定向
                children: [{
                    path: 'infoBase',
                    name: 'InfoBase',
                    component: () => import("@/views/InfoBase"),
                }, {
                    path: 'infoGoods',
                    name: 'InfoGoods',
                    component: () => import("@/views/InfoGoods"),
                }, {
                    path: 'infoOrder',
                    name: 'InfoOrder',
                    component: () => import("@/views/InfoOrder"),
                }]
            },
        ]
    },
    {
        path: '/manage',
        name: 'Manage',
        component: Manage,
        redirect: "/manageLogin",      //重定向

        children: [
            {
                path: 'user',
                name: 'UserView',
                component: () => import("@/views/UserView"),
            },
            {
                path: 'goods',
                name: 'GoodsView',
                component: () => import("@/views/GoodsView"),
            }, {
                path: 'order',
                name: 'OrderView',
                component: () => import("@/views/OrderView"),
            },

        ]
    },

    {
        path: '/manageLogin',
        name: 'ManageLoginView',
        component: () => import("@/views/ManageLoginView")
    },


]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
