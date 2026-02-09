import Vue from 'vue'
import App from './App.vue'
import 'weui/dist/style/weui.min.css'

Vue.config.productionTip = false

new Vue({
  render: h => h(App)
}).$mount('#app')
