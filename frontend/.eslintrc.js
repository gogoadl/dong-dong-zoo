/**
 * .eslint.js
 *
 * ESLint configuration file.
 */

module.exports = {
  root: true,
  env: {
    node: true,
    // jquery: true,
  },
  extends: [
    "plugin:vue/vue3-essential",
    "eslint:recommended",
    "@vue/eslint-config-typescript",
  ],
  rules: {
    "vue/multi-word-component-names": "off",
  },
};
