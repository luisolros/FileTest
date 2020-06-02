To be compiled/executed with Java 8.

Usage: 
java -jar FileTest.jar [inputFileName] [outputFileName] [sortBy] [formatted] [delimiter]

Arguments:
[inputFileName] Input plain text file name (absolute path). First line must specify File Type ('1' or '2').
[outputFileName] Output file name (absolute path).
[sortBy] 'FIRST_NAME' or  'LAST_NAME' or 'START_DATE' values. Data in output file will be sorted in ascending order based on this field.
[formatted] 'true' or 'false'. if true, returns data formatted as plain text.
[delimiter] Optional argument. Default is comma (',') if not specified. Delimiter to be used in case of File Type '2'only.

Example:
java -jar FileTest.jar C:\Tests\TestFile_2.txt C:\Tests\output.xml FIRST_NAME false

Some testing files may be provided.