/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.isi.wings.portal.classes.html;

import java.io.PrintWriter;
import java.util.HashMap;

import edu.isi.wings.portal.classes.Config;
import edu.isi.wings.portal.classes.JsonHandler;

import com.google.gson.Gson;

public class JSLoader {
	static String[] common_scripts = { "lib/extjs/ext-all.js", "js/util/common.js",
			"js/gui/WingsMessages.js", "js/util/TreeMod.js", "js/gui/ProvenanceViewer.js" };
	static String[] component_scripts = { "js/gui/ComponentViewer.js" };
	static String[] domain_scripts = { "js/gui/DomainViewer.js" };
	static String[] run_scripts = { "js/gui/RunBrowser.js" };
	static String[] data_scripts = { "js/gui/DataViewer.js" };
	static String[] user_scripts = { "js/gui/UserViewer.js" };
	static String[] resource_scripts = { "js/gui/ResourceViewer.js" };
	static String[] template_scripts = { "js/gui/SeedForm.js", "js/gui/template/Canvas.js",
			"js/gui/template/port.js", "js/gui/template/shape.js", "js/gui/template/link.js",
			"js/gui/template/node.js", "js/gui/template/variable.js", "js/gui/template/template.js",
			"js/gui/template/layout.js", "js/gui/template/reasoner.js",
			"js/gui/TemplateBrowser.js", "js/gui/TemplateGraph.js", };
	static String[] rule_scripts = { "js/gui/jenarules/RulesParser.js",
			"js/gui/jenarules/RuleFunctionType.js", "js/gui/jenarules/RuleFunction.js",
			"js/gui/jenarules/Rule.js", "js/gui/jenarules/Constants.js", 
			"js/gui/jenarules/Triple.js", "js/gui/jenarules/LocalClasses.js" };
	static String[] tellme_scripts = { "js/gui/tellme/tellme.js",
			"js/gui/tellme/tellme_history.js", "js/beamer/ControlList.js", "js/beamer/MatchedTask.js",
			"js/beamer/Paraphrase.js", "js/beamer/Task.js", "js/beamer/TodoItemMatches.js",
			"js/beamer/TodoListParser.js", "js/beamer/Token.js", "js/beamer/Trie.js", 
			"js/util/lz-string-min.js"};

	static String[] plupload_scripts = { "js/util/pluploadPanel.js", "lib/plupload/plupload.full.min.js" };

	public static void loadConfigurationJS(PrintWriter out, Config config) {
		HashMap<String, Object> jsvars = new HashMap<String, Object>();
    jsvars.put("DOMAIN_ID", config.getDomainId());
    jsvars.put("USER_ID",  config.getUserId());
    jsvars.put("VIEWER_ID",  config.getViewerId());
    
    jsvars.put("CONTEXT_ROOT",  config.getContextRootPath());
    jsvars.put("COM_ROOT", config.getCommunityPath());
    jsvars.put("USER_ROOT", config.getUserPath());
    jsvars.put("USERDOM_ROOT", config.getUserDomainUrl());
    jsvars.put("SCRIPT_PATH", config.getScriptPath());
    
    jsvars.put("DOMAINS", config.getDomainsList());
    jsvars.put("USERS", config.getUsersList());
    jsvars.put("ISADMIN", config.isAdminViewer());
    
		JSLoader.showScriptKeyVals(out, jsvars);
	}

	public static void loadLoginViewer(PrintWriter out, String path) {
		showScriptTags(out, path, common_scripts);		
	}
		
	public static void loadDataViewer(PrintWriter out, String path) {
		showScriptTags(out, path, common_scripts);
		showScriptTags(out, path, data_scripts);
		showScriptTags(out, path, plupload_scripts);
	}

  public static void loadUserViewer(PrintWriter out, String path) {
    showScriptTags(out, path, common_scripts);
    showScriptTags(out, path, user_scripts);
  }

	public static void loadComponentViewer(PrintWriter out, String path) {
		showScriptTags(out, path, common_scripts);
		showScriptTags(out, path, component_scripts);
		//showScriptTags(out, path, rule_scripts);
		showScriptTags(out, path, plupload_scripts);
	}

	public static void loadDomainViewer(PrintWriter out, String path) {
		showScriptTags(out, path, common_scripts);
		showScriptTags(out, path, domain_scripts);
		showScriptTags(out, path, plupload_scripts);
	}

	 public static void loadResourceViewer(PrintWriter out, String path) {
	    showScriptTags(out, path, common_scripts);
	    showScriptTags(out, path, resource_scripts);
	  }
	 
	public static void loadRunViewer(PrintWriter out, String path) {
		showScriptTags(out, path, common_scripts);
		showScriptTags(out, path, run_scripts);
		showScriptTags(out, path, template_scripts);
	}

	public static void loadTemplateViewer(PrintWriter out, String path, boolean loadtellme) {
		showScriptTags(out, path, common_scripts);
		showScriptTags(out, path, template_scripts);
		showScriptTags(out, path, component_scripts);
		if (loadtellme)
			showScriptTags(out, path, tellme_scripts);
	}

	private static void showScriptTags(PrintWriter out, String path, String[] scripts) {
		for (String script : scripts) {
			out.println("<script src=\"" + path + "/" + script + "\"></script>");
		}
	}

	public static void showScriptKeyVals(PrintWriter out, HashMap<String, Object> map) {
	  Gson json = JsonHandler.createGson();
		out.println("<script>");
		for (String key : map.keySet()) {
			Object val = map.get(key);
			out.println("var " + key + " = " + json.toJson(val) + ";");
		}
		out.println("</script>");
	}
}
