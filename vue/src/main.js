import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import "@/assets/css/global.css"
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'dayjs/locale/zh-cn'
import locale from 'element-ui/lib/locale/lang/zh-CN'

Vue.config.productionTip = false
Vue.use(ElementUI, {locale});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
