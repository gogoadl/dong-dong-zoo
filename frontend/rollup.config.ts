import inject from "@rollup/plugin-inject";

export default {
  input: "src/main.ts",
  output: {
    dir: "output",
    format: "cjs",
  },
  plugins: [
    inject({
      Promise: ["es6-promise", "Promise"],
    }),
  ],
};
