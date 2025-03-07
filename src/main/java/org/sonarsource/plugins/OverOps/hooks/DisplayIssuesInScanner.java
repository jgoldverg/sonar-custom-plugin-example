/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.OverOps.hooks;

import org.sonar.api.batch.postjob.PostJob;
import org.sonar.api.batch.postjob.PostJobContext;
import org.sonar.api.batch.postjob.PostJobDescriptor;
import org.sonar.api.batch.postjob.issue.PostJobIssue;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class DisplayIssuesInScanner implements PostJob {

  private static final Logger LOGGER = Loggers.get(DisplayIssuesInScanner.class);

  @Override
  public void describe(PostJobDescriptor descriptor) {
    descriptor.name("Display issues");
  }

  @Override
  public void execute(PostJobContext context) {
    // issues are not accessible when the mode "issues" is not enabled
    // with the scanner property "sonar.analysis.mode=issues"
    if (context.analysisMode().isIssues()) {
      // all open issues
      for (PostJobIssue issue : context.issues()) {
        String ruleKey = issue.ruleKey().toString();
        Integer issueLine = issue.line();

        // just to illustrate, we dump some fields of the 'issue' in sysout (bad, very bad)
        LOGGER.info("OPEN {} : {}({})", ruleKey, issue.componentKey(), issueLine);
      }

      // all resolved issues
      for (PostJobIssue issue : context.resolvedIssues()) {
        LOGGER.info("RESOLVED {} : {}({})", issue.ruleKey(), issue.componentKey(), issue.line());
      }
    }
  }
}
