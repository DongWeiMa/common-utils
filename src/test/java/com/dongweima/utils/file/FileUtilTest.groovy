package com.dongweima.utils.file

import spock.lang.Specification

import static org.junit.Assert.assertArrayEquals

class FileUtilTest extends Specification {

  def setup() {
  }

  def "test get Lines"() {
    given:
    String filePath = new File(PathUtil.getBaseDir(), "test" + File.separator + "file").getPath()

    when:
    LinkedList<String> result = FileUtil.getLines(filePath)

    then:
    assertArrayEquals result.toArray(), lines.toArray()

    where:
    lines      | _
    ["1", "2"] | _
  }

  void "get file paths under ./data but just verify the file number"() {
    when:
    List<String> paths = FileUtil.getFilePaths()
    then:
    paths.size() == 2
  }

  void "test creat file"() {
    given:
    String filePath = "a/test.txt";
    File file = new File(PathUtil.getBaseDir(), filePath)
    if (file.exists()) {
      file.delete()
    }

    when:
    FileUtil.createFileInClassPath(file.getPath())

    then:
    file.exists()

    cleanup:
    if (file.exists()) {
      file.delete()
    }
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme