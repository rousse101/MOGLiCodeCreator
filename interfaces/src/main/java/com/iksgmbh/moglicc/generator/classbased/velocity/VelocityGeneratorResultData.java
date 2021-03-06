package com.iksgmbh.moglicc.generator.classbased.velocity;

import java.io.File;

import com.iksgmbh.moglicc.MOGLiSystemConstants;
import com.iksgmbh.moglicc.exceptions.MOGLiPluginException;
import com.iksgmbh.moglicc.generator.GeneratorResultData;

/**
 * This class extends the abstract definition of {@link GeneratorResultData}.
 * It is used from the Velocity based generators and the velocity engine provider.
 *
 * @author Reik Oberrath
 * @since 1.0.0
 */
public interface VelocityGeneratorResultData extends GeneratorResultData {

	static final String PACKAGE = "package";
	static final String PACKAGE_IDENTIFIER = "<" + PACKAGE + ">";  // REFACTOREME: This is a special solution for generating a JavaBean - the mechanism that uses this identifier should be done somewhere else  
	static final String ROOT_IDENTIFIER = MOGLiSystemConstants.APPLICATION_ROOT_IDENTIFIER;

	static final String NO_TARGET_FILE_NAME = "TargetFileName not defined.";
	static final String NO_TARGET_DIR = "TargetDir not defined.";

	public static final String TEXT_TARGET_DIR_NOT_FOUND = "Defined target directory does not exist:";
	public static final String TEXT_TARGET_DIR_IS_A_FILE = "Defined target directory is no directory";
	public static final String TEXT_TARGET_FILE_NOT_FOUND = "Defined target file does not exist";
	public static final String TEXT_TARGET_FILE_IS_A_DIRECTORY = "Defined target file is no file";
	public static final String TEXT_PACKAGE_NOT_FOUND = "The placeholder '" + PACKAGE + "' cannot be not replaced, because no information about the package is available. " 
	                                                     + System.getProperty("line.separator") + 
	                                                     "Typical reason is, that for generating a JavaBean at least one class is used which is not "
	                                                     + "fully qualified (missing package or no leading capital letter). "
	                                                     + System.getProperty("line.separator")  
	                                                     + "Please check the classes defined in the model file.";

	enum KnownGeneratorPropertyNames { TargetDir, TargetFileName, CreateNew, NameOfValidModel, OutputEncodingFormat, SkipGeneration };

	String getTargetDir();

	String getTargetFileName();

	/**
	 * Provides information whether target file has to be overwritten if existing
	 * @return false if target file must remain untouched
	 */
	boolean isTargetToBeCreatedNewly();
	
	/**
	 * Provides information whether the code supposed to create has to be ignored for generation
	 * @return false if creation of target file is suppressed
	 */
	boolean skipGeneration();


	/**
	 * Provides information whether or not the target file has not been overwritten.
	 * @return false either if target file was not existent (and was newly created)
	 *               or was overwritten because isTargetToBeCreatedNewly has returned true
	 */
	boolean wasExistingTargetPreserved();

	void setExistingTargetPreserved(boolean existingTargetPreserved);

	/**
	 * For null the underlying template is valid -> per default a template is applied to all models.
	 * For invalid templates this result will be ignored!
	 * @return name of model for which the underlying template is valid
	 */
	String getNameOfValidModel();

	/**
	 * @return name of charset uses to create output files
	 */
	String getOutputEncodingFormat();

	/**
	 * Replaces dynamically PACKAGE_IDENTIFIER and ROOT_IDENTIFIER with real values
	 * and checks that it exists
	 * @param applicationRootDir
	 * @param pathAdaption neccassary for test purpose
	 * @return null if not defined in main template
	 * @throws MOGLiPluginException
	 */
	File getTargetDirAsFile(String applicationRootDir, String pathAdaption) throws MOGLiPluginException;

	/**
	 * Uses {@code getTargetDirAsFile} to create target file object and checks that it exists
	 * @param applicationRootDir
	 * @param pathAdaption neccassary for test purpose
	 * @return null if not defined in main template
	 * @throws MOGLiPluginException
	 */
	File getTargetFile(String applicationRootDir, String pathAdaptation) throws MOGLiPluginException;

	/**
	 * @throws MOGLiPluginException if conflicting or missing settings exist
	 */
	void validatePropertyKeys(String artefact) throws MOGLiPluginException;

	/**
	 * @throws MOGLiPluginException if conflicting or missing settings exist
	 */
	void validatePropertyForMissingMetaInfoValues(String artefact) throws MOGLiPluginException;

}