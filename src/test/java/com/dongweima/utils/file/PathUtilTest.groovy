package com.dongweima.utils.file

import org.junit.Test

import static org.junit.Assert.assertTrue

class PathUtilTest {
  @Test
  void test_get_base_dir() {
    String baseDir = PathUtil.getBaseDir()
    File file = new File(baseDir, "readme")
    assertTrue file.exists()
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme