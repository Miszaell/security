package com.ex.security.Mapper;

public interface IMapper <In , Out>{
    public Out map(In in);
}