package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.converter;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class TypeConverter implements UserType<AppUser.Role> {
    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<AppUser.Role> returnedClass() {
        return AppUser.Role.class;
    }

    @Override
    public boolean equals(AppUser.Role role, AppUser.Role j1) {
        return role.name().equals(j1.name());
    }

    @Override
    public int hashCode(AppUser.Role role) {
        return 0;
    }

    @Override
    public AppUser.Role nullSafeGet(ResultSet resultSet, int i,
                                    SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        return AppUser.Role.valueOf(resultSet.getString(i));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, AppUser.Role role,
                            int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if(role!=null) {
            preparedStatement.setObject(i, role.name(), Types.OTHER);
        }
    }

    @Override
    public AppUser.Role deepCopy(AppUser.Role role) {
        return role;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(AppUser.Role role) {
        return null;
    }

    @Override
    public AppUser.Role assemble(Serializable serializable, Object o) {
        return null;
    }

    @Override
    public AppUser.Role replace(AppUser.Role role, AppUser.Role j1, Object o) {
        return j1;
    }
}
