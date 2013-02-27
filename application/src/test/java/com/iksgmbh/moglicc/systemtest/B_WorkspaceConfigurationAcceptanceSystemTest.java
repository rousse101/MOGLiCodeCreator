package com.iksgmbh.moglicc.systemtest;

import java.io.File;

import org.junit.Test;

import com.iksgmbh.moglicc.MOGLiSystemConstants;
import com.iksgmbh.moglicc.MOGLiTextConstants;
import com.iksgmbh.moglicc.provider.model.standard.StandardModelProviderStarter;
import com.iksgmbh.moglicc.utils.MOGLiFileUtil;
import com.iksgmbh.utils.CmdUtil;
import com.iksgmbh.utils.FileUtil;

public class B_WorkspaceConfigurationAcceptanceSystemTest extends _AbstractSystemTest {

	@Test
	public void doesNotExecuteDeactivatedPlugin() throws Exception {
		// prepare test
		final File workspacePropertiesFile = new File(applicationRootDir, MOGLiSystemConstants.FILENAME_WORKSPACE_PROPERTIES);
		MOGLiFileUtil.createNewFileWithContent(workspacePropertiesFile, "com.iksgmbh.moglicc.provider.model.standard="
				                                                        + MOGLiTextConstants.TEXT_ACTIVATED_PLUGIN_PROPERTY);

		// call functionality under test
		executeMogliApplication();

		// cleanup
		workspacePropertiesFile.delete();

		// verify test result
		assertChildrenNumberInDirectory(applicationOutputDir, 1);
		final File pluginOutputDir = new File(applicationOutputDir, StandardModelProviderStarter.PLUGIN_ID);
		assertFileExists(pluginOutputDir);
	}

	@Test
	public void createsWorkspaceDirProvidedAsArgumentOfStartBatchFile() throws Exception {
		// prepare test
		final String workspace = "workspaces/test3";
		final String startBat = "start.bat";
		final File workspaceDir = initWorkspaceDir(workspace);
		final File startFile = new File(applicationRootDir, startBat);
		MOGLiFileUtil.createNewFileWithContent(startFile, "startMOGLiCodeCreator.bat " + workspace);

		// call functionality under test
		CmdUtil.execWindowCommand(testDir, startBat, true);

		// cleanup
		startFile.delete();

		// verify test result
		assertWorkspace(workspaceDir);
	}

	@Test
	public void createsWorkspaceDirConfiguredInStartBatchFile() throws Exception {
		// prepare test
		final File workspaceDir = initWorkspaceDir("workspaces/test2");
		setCustomizedStartBatchFile();

		// call functionality under test
		executeMogliApplication();

		// cleanup
		resetDefaultStartBatchFile();

		// verify test result
		assertWorkspace(workspaceDir);
	}

	@Test
	public void createsWorkspaceConfiguredInApplicationPropertiesFile() throws Exception {
		// prepare test
		final String workspaceDirName =  "workspaces/test";
		final File applicationPropertiesFile = initApplicationPropertiesFile(
				MOGLiSystemConstants.APPLICATION_ROOT_IDENTIFIER
				 + "/" + workspaceDirName);
		assertFileExists(applicationPropertiesFile);
		final File workspaceDir = initWorkspaceDir(workspaceDirName);

		// call functionality under test
		executeMogliApplication();

		// cleanup
		applicationPropertiesFile.delete();

		// verify test result
		assertWorkspace(workspaceDir);
	}

	@Test
	public void createsDefaultWorkspace() throws Exception {
		// call functionality under test
		executeMogliApplication();

		// verify test result
		assertWorkspace(applicationRootDir);
	}


	// *************************************************************************************
	//                       helper methods
	// *************************************************************************************


	private void assertWorkspace(final File workspaceDir) {
		assertFileExists(workspaceDir);
		final File inputDir = new File(workspaceDir, MOGLiSystemConstants.DIR_INPUT_FILES);
		assertFileExists(inputDir);
		final File outputDir = new File(workspaceDir, MOGLiSystemConstants.DIR_OUTPUT_FILES);
		assertFileExists(outputDir);
		final File logsDir = new File(workspaceDir, MOGLiSystemConstants.DIR_LOGS_FILES);
		assertFileExists(logsDir);

		assertWorkspacePropertiesFile(workspaceDir);
	}

	private void assertWorkspacePropertiesFile(File workspaceDir) {
		final File workspacePropertiesFile = new File(workspaceDir, MOGLiSystemConstants.FILENAME_WORKSPACE_PROPERTIES);
		assertFileExists(workspacePropertiesFile);
		final File expectedFile = new File(PROJECT_ROOT_DIR + "../core/src/main/resources/"
				                           + MOGLiSystemConstants.FILENAME_WORKSPACE_PROPERTIES);
		assertFileEquals(expectedFile, workspacePropertiesFile);
	}

	private File initApplicationPropertiesFile(final String workspaceDirName) throws Exception {
		final File applicationPropertiesFile = new File(applicationRootDir, MOGLiSystemConstants.FILENAME_APPLICATION_PROPERTIES);
		FileUtil.createNewFileWithContent(applicationPropertiesFile, MOGLiSystemConstants.WORKSPACE_PROPERTY + "=" + workspaceDirName);
		return applicationPropertiesFile;
	}

	private File initWorkspaceDir(final String workspaceDirName) throws Exception {
		final File workspaceDir = new File(applicationRootDir, workspaceDirName);
		assertFileDoesNotExist(workspaceDir);
		return workspaceDir;
	}

	private void setCustomizedStartBatchFile() {
		final File sourceFile = new File(getProjectTestResourcesDir(), "startBatWithCustomizedWorkspaceDir.bat");
		final File targetFile = new File(applicationRootDir, "startMOGLiCodeCreator.bat");
		MOGLiFileUtil.createNewFileWithContent(targetFile, MOGLiFileUtil.getFileContent(sourceFile));
	}

	private void resetDefaultStartBatchFile() {
		final File sourceFile = new File(getProjectResourcesDir(), "release/startMOGLiCodeCreator.bat");
		final File targetFile = new File(applicationRootDir, "startMOGLiCodeCreator.bat");
		MOGLiFileUtil.createNewFileWithContent(targetFile, MOGLiFileUtil.getFileContent(sourceFile));
	}
}
