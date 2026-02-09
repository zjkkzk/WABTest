<template>
  <div id="app">
    <div class="page">
      <header class="page__header">
        <div class="page__title">实验配置</div>
        <div class="page__subtitle">勾选后保存即可生效</div>
      </header>

      <div class="page__summary">
        <span>已启用 {{ enabledCount }} / {{ totalCount }}</span>
      </div>

      <div class="weui-form page__card">
        <div class="weui-form__bd">
          <div
            v-for="group in normalizedGroups"
            :key="group.version || 'unknown'"
            class="group"
          >
            <div class="group__title">
              {{ group.version || '未标注版本' }}
              <span class="group__count">({{ group.items.length }})</span>
            </div>
            <div class="weui-cells__group weui-cells__group_form">
              <div class="weui-cells weui-cells_checkbox">
                <label
                  v-for="config in group.items"
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
                    <p class="cell-title">{{ config.name }}</p>
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

      <footer class="page__footer">如需生效，请返回应用刷新页面</footer>
    </div>
  </div>
</template>

<script>
import configGroups from './config.json'

export default {
  name: 'App',
  data() {
    return {
      configGroups,
      checked: {}
    }
  },
  computed: {
    enabledCount() {
      return Object.values(this.checked).filter(Boolean).length
    },
    normalizedGroups() {
      return this.configGroups
        .map((group) => {
          const version = Object.keys(group)[0] || ''
          const items = group[version] || []
          return { version, items }
        })
        .filter((group) => group.items.length > 0)
    },
    flatConfigs() {
      return this.normalizedGroups.flatMap((group) => group.items)
    },
    totalCount() {
      return this.flatConfigs.length
    }
  },
  mounted() {
    const localArgs = this.loadLocalArgs()
    const localMap = new Map(localArgs.map((arg) => [arg.Key, arg.Val]))
    this.flatConfigs.forEach((config) => {
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
      const args = this.flatConfigs.map((config) => ({
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
:root {
  --card: #ffffff;
  --text: #1f2933;
  --subtle: #52606d;
  --accent: #0f8b8d;
  --accent-dark: #0a6a6b;
  --border: rgba(31, 41, 51, 0.08);
}

#app {
  min-height: 100vh;
  background: linear-gradient(180deg, #eef6f6 0%, #f4f6f8 30%, #f4f6f8 100%);
  color: var(--text);
  font-family: 'PingFang SC', 'Microsoft YaHei', 'Source Han Sans SC', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.page {
  max-width: 680px;
  margin: 0 auto;
  padding: 24px 16px 28px;
}

.page__header {
  padding: 8px 4px 16px;
}

.page__title {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.page__subtitle {
  margin-top: 6px;
  font-size: 13px;
  color: var(--subtle);
}

.page__summary {
  margin: 4px 4px 14px;
  padding: 10px 12px;
  border-radius: 10px;
  background: rgba(15, 139, 141, 0.08);
  color: var(--accent-dark);
  font-size: 13px;
}

.page__card {
  background: var(--card);
  border-radius: 16px;
  border: 1px solid var(--border);
  box-shadow: 0 10px 24px rgba(15, 139, 141, 0.08);
  overflow: hidden;
}

.page__card .weui-cells {
  margin-top: 0;
}

.group {
  padding: 6px 0 2px;
}

.group__title {
  padding: 6px 16px 4px;
  font-size: 13px;
  font-weight: 600;
  color: var(--subtle);
  line-height: 1.2;
}

.group__count {
  margin-left: 6px;
  font-weight: 400;
  color: rgba(82, 96, 109, 0.8);
}

.page__card .weui-cells:before,
.page__card .weui-cells:after {
  display: none;
}

.page__card .weui-cell {
  padding: 14px 16px;
}

.page__card .weui-cell:before {
  left: 16px;
  right: 16px;
  border-top-color: var(--border);
}

.cell-title {
  font-size: 15px;
}

.page__card .weui-icon-checked {
  background-image: url("data:image/svg+xml,%3Csvg width='25' height='24' viewBox='0 0 25 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Crect x='1.1' y='0.6' width='22.8' height='22.8' rx='11.4' stroke='%2399A3AD' stroke-opacity='1' stroke-width='1.2'/%3E%3C/svg%3E") !important;
}

.page__card .weui-check:checked + .weui-icon-checked {
  background-image: url("data:image/svg+xml,%3Csvg width='25' height='24' viewBox='0 0 25 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Crect x='0.5' width='24' height='24' rx='12' fill='%230F8B8D'/%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M10.2712 16.2899L6.5 12.5187L7.44281 11.5759L10.7426 14.8757L18.2851 7.33325L19.2279 8.27606L11.214 16.2899C10.9537 16.5503 10.5316 16.5503 10.2712 16.2899Z' fill='white'/%3E%3C/svg%3E") !important;
}

.page__card .weui-btn_primary {
  background: var(--accent);
}

.page__card .weui-btn_primary:active {
  background: var(--accent-dark);
}

.page__footer {
  margin-top: 14px;
  padding: 0 6px;
  font-size: 12px;
  color: var(--subtle);
}

@media (min-width: 720px) {
  .page {
    padding-top: 36px;
  }
  .page__title {
    font-size: 24px;
  }
}
</style>
