// https://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint'
  },
  env: {
    browser: true,
  },
  extends: [
    // https://github.com/vuejs/eslint-plugin-vue#priority-a-essential-error-prevention
    // consider switching to `plugin:vue/strongly-recommended` or `plugin:vue/recommended` for stricter rules.
    'plugin:vue/essential', 
    // https://github.com/standard/standard/blob/master/docs/RULES-en.md
    'standard'
  ],
  // required to lint *.vue files
  plugins: [
    'vue'
  ],
  // add your custom rules here
  rules: {
    // allow async-await
    'generator-star-spacing': 'off', // 禁用 ESLint 对生成器的星号(*)周围的空格的检查
    "no-trailing-spaces": "off",  // 禁用 ESLint 对“尾部空格”的报错
    "comma-dangle": "off", // 禁用 ESLint 对“尾部逗号”的报错
    "comma-spacing": "off",  // 禁用 ESLint 对逗号后缺少空格的检查
    "indent":"off",  // 禁用 ESLint 的缩进检查
    "quotes":"off",  // 禁用 ESLint 对字符串的引号形式的检查
    "key-spacing": "off",  // 禁用 ESLint 对对象内key和value之前空格的检查
    "no-multiple-empty-lines": "off",  // 禁用 ESLint 对多行空行的检查
    "arrow-spacing": "off",  // 禁用 ESLint 对箭头函数空格的检查
    "semi": "off",  // 禁用 ESLint 对分号的检查
    "eol-last": "off",  // 禁用 ESLint 对文件末尾换行的检查
    "no-multi-spaces": "off",  // 禁用 ESLint 对多余空格的检查
    "space-before-blocks": "off",  // 取消对左大括号前缺少空格的检查
    "space-before-function-paren": "off",  // 禁用 ESLint 对函数括号前缺少空格的检查
    "space-infix-ops": "off", // 禁用 ESLint 对中缀操作符周围缺少空格的检查
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
  }
}
