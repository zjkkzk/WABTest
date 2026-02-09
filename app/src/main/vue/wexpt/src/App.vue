<template>
  <div id="app">
    <div class="weui-form">
      <div class="weui-form__bd">
        <div class="weui-form__control-area">
          <div class="weui-cells__group weui-cells__group_form">
            <div class="weui-cells weui-cells_checkbox">
              <label
                v-for="config in configs"
                :key="config.key"
                class="weui-cell weui-cell_active weui-check__label"
              >
                <div class="weui-cell__hd">
                  <input
                    type="checkbox"
                    class="weui-check"
                    :id="config.key"
                    v-model="checked[config.key]"
                  />
                  <i class="weui-icon-checked"></i>
                </div>
                <div class="weui-cell__bd">
                  <p>{{ config.name }}</p>
                </div>
              </label>
            </div>
          </div>
        </div>
      </div>
      <div class="weui-form__ft">
        <div class="weui-form__opr-area">
          <a role="button" class="weui-btn weui-btn_primary" href="javascript:" @click="save">
            保存
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import configs from './config.json'

export default {
  name: 'App',
  data() {
    return {
      configs,
      checked: {}
    }
  },
  mounted() {
    const localArgs = this.loadLocalArgs()
    const localMap = new Map(localArgs.map((arg) => [arg.Key, arg.Val]))
    this.configs.forEach((config) => {
      const localVal = localMap.get(config.key)
      this.$set(this.checked, config.key, localVal === config.trueVal)
    })
  },
  methods: {
    loadLocalArgs() {
      if (window.WExpt && window.WExpt.getExptArgs) {
        try {
          const json = window.WExpt.getExptArgs()
          return JSON.parse(json) || []
        } catch (error) {
          return []
        }
      }
      return []
    },
    save() {
      const args = this.configs.map((config) => ({
        Key: config.key,
        Val: this.checked[config.key] ? config.trueVal : config.falseVal
      }))
      if (window.WExpt && window.WExpt.putExptArgs) {
        try {
          window.WExpt.putExptArgs(JSON.stringify(args))
          alert('保存成功')
        } catch (error) {
          alert('保存出错: 调用失败')
        }
      } else {
        alert('保存出错: 接口异常')
      }
    }
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
