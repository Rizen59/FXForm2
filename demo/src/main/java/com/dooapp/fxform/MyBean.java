/*
 * Copyright (c) 2012, dooApp <contact@dooapp.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of dooApp nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dooapp.fxform;

import com.dooapp.fxform.adapter.FormAdapter;
import com.dooapp.fxform.annotation.FormFactory;
import com.dooapp.fxform.validation.PasswordMatch;
import com.dooapp.fxform.validation.Warning;
import com.dooapp.fxform.view.factory.impl.PasswordFieldFactory;
import com.dooapp.fxform.view.factory.impl.TextAreaFactory;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created at 26/09/12 14:23.<br>
 *
 * @author Antoine Mischler <antoine@dooapp.com>
 */
@PasswordMatch
public class MyBean {

    public static enum Subject {
        CONTACT, QUESTION, BUG, FEEDBACK
    }

    private final StringProperty name = new SimpleStringProperty();

    private final ReadOnlyStringProperty welcome = new SimpleStringProperty();

    private final StringProperty email = new SimpleStringProperty();

    @FormFactory(PasswordFieldFactory.class)
    private final StringProperty password = new SimpleStringProperty();

    @FormFactory(PasswordFieldFactory.class)
    private final StringProperty repeatPassword = new SimpleStringProperty();

    private final BooleanProperty subscribe = new SimpleBooleanProperty();

    private final ReadOnlyBooleanProperty unsubscribe = new SimpleBooleanProperty();

    private final ObjectProperty<Subject> subject = new SimpleObjectProperty<Subject>();

    private final IntegerProperty year = new SimpleIntegerProperty();

    @FormAdapter(BigDecimalAdapter.class)
    private final ObjectProperty<BigDecimal> bigDecimalProperty = new SimpleObjectProperty<BigDecimal>();

    @FormFactory(TextAreaFactory.class)
    private final StringProperty message = new SimpleStringProperty();

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

    private final ObjectProperty<Color> color = new SimpleObjectProperty<Color>();

    private final ListProperty<TableBean> list = new SimpleListProperty<TableBean>(FXCollections.<TableBean>observableArrayList());

    protected MyBean(String name, String email, String message, boolean subscribe, Subject subject) {
        this.name.set(name);
        this.email.set(email);
        this.message.set(message);
        this.subscribe.set(subscribe);
        this.subject.set(subject);
        this.list.addAll(new TableBean("Name 1", 99), new TableBean("Name 2", 98));
        ((StringProperty) welcome).bind(this.name.concat(", welcome!"));
        ((BooleanProperty) unsubscribe).bind(this.subscribe.not());
    }

    /**
     * This constraints uses the warning group, which means that the model value will still be updated by the form
     * even if the value entered by the user violates this constraint.
     *
     * @return
     */
    public String getMessage() {
        return message.get();
    }

    /**
     * This constraint uses the Default group, which is treated by FXForm as a strict validation. The model value
     * won't be updated if the value entered by the user violates this constraint.
     *
     * @return
     */
    @Email
    public String getEmail() {
        return email.get();
    }

    @NotNull
    public BigDecimal getBigDecimalProperty() {
        return bigDecimalProperty.get();
    }

    @Max(value = 2013, groups = Warning.class)
    public Integer getYear() {
        return year.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getRepeatPassword() {
        return repeatPassword.get();
    }

}