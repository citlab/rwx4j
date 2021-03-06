/**
 * Copyright 2010-2015 Complex and Distributed IT Systems, TU Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tu_berlin.cit.rwx4j.component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a white list for all known trusted root server. 
 * 
 * @author Alexander Stanik <alexander.stanik@tu-berlin.de>
 */
public class RootServerWhitelist {

	public static final List<String> list;

	static {
		list = new ArrayList<String>();

		// root server
		list.add("root.intercloud-network.de");
		// list.add("root.intercloudtestbed.org");
	}

}
