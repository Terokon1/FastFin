package com.chaev.fastfin.domain.exceptions

import java.lang.RuntimeException

class MappingException(field: String): RuntimeException("Mapping failed on $field")