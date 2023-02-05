package com.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountBook is a Querydsl query type for AccountBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountBook extends EntityPathBase<AccountBook> {

    private static final long serialVersionUID = -1746538759L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountBook accountBook = new QAccountBook("accountBook");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> accDate = createDateTime("accDate", java.time.LocalDateTime.class);

    public final StringPath accDtlMemo = createString("accDtlMemo");

    public final StringPath accStatus = createString("accStatus");

    public final StringPath accTitle = createString("accTitle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final NumberPath<Integer> money = createNumber("money", Integer.class);

    public final StringPath otherCtgName = createString("otherCtgName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final QSubCategory subCategory;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> upDateTime = _super.upDateTime;

    public QAccountBook(String variable) {
        this(AccountBook.class, forVariable(variable), INITS);
    }

    public QAccountBook(Path<? extends AccountBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountBook(PathMetadata metadata, PathInits inits) {
        this(AccountBook.class, metadata, inits);
    }

    public QAccountBook(Class<? extends AccountBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCategory(forProperty("subCategory"), inits.get("subCategory")) : null;
    }

}

