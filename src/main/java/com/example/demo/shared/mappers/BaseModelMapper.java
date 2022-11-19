package com.example.demo.shared.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class BaseModelMapper {
  public BaseModelMapper() {}

  private static ModelMapper modelMapper;

  public static <T> T mapper(Object sourceData, Class<T> destinationClassType) {
    return getInstance().map(sourceData, destinationClassType);
  }

  public static <T> T mapper(Object sourceData, T targetData) {
    getInstance().map(sourceData, targetData);
    return targetData;
  }

  public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
    return source.stream().map(e -> getInstance().map(e, targetClass)).collect(Collectors.toList());
  }

  public static <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
    return source.map(e -> getInstance().map(e, targetClass));
  }

  private static ModelMapper getInstance() {
    if (modelMapper == null) {
      modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
      modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
    }
    return modelMapper;
  }
}
