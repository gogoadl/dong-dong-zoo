/**
 * plugins/index.ts
 *
 * Automatically included in `./src/main.ts`
 */

// Plugins
import vuetify from "./vuetify";
import pinia from "../store";
import router from "../router";
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
/* import font awesome icon component */
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// Types
import type { App } from "vue";

export function registerPlugins(app: App) {
  library.add(fas);
  app
    .use(vuetify)
    .use(router)
    .use(pinia)
    .component("font-awesome-icon", FontAwesomeIcon);
}
