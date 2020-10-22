package com.business.pound.util.converter;


import java.io.Serializable;

public interface PersistEnum2DB<DB> extends Serializable {
    DB getData();
}
