package com.example.demo.common;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  private Utils() {

  }

  /**
   * Transfer data from obj A to obj B
   *
   * @param oldData Obj B
   * @param newData Obj A
   * @param classType Class type want to tranfer
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static <T> T convertTo(Object oldData, Object newData, Class<?> classType)
      throws IllegalArgumentException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    Map<String, Object> mapOld = mapper.convertValue(oldData, LinkedHashMap.class);
    Map<String, Object> mapNew = convertTo(newData, mapper);

    mapOld.forEach((k, v) -> {
      try {
        if (mapNew.get(k) != null) {
          Object newValue = mapNew.get(k);
          Class<?> fieldType = classType.getDeclaredField(snakeCaseToPascalCase(k)).getType();

          if (newValue instanceof LinkedHashMap) {
            mapOld.put(k, newValue);
          } else if (fieldType.isEnum()) {
            mapOld.put(k, newValue.toString());
          } else {
            mapOld.put(k, newValue);
          }
        }
      } catch (NoSuchFieldException e) {
        log.error(e.getMessage());
      } catch (SecurityException e) {
        log.error(e.getMessage());
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    });

    oldData = mapper.convertValue(mapOld, classType);

    return (T) oldData;
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> convertTo(Object data, ObjectMapper mapper)
      throws IOException {
    Map<String, Object> res = null;

    if (data instanceof String) {
      String json = data.toString().replaceAll("\\\"", "\"");
      res = mapper.readValue(json, LinkedHashMap.class);
    } else {
      res = mapper.convertValue(data, LinkedHashMap.class);
    }

    return res;
  }

  /**
   * Format from snake_case to pascalCase (except Id)
   *
   * @param name
   * @return
   */
  private static String snakeCaseToPascalCase(String name) {
    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
  }

}
