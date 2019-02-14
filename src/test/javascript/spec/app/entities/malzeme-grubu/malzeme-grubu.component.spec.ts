/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeGrubuComponent } from 'app/entities/malzeme-grubu/malzeme-grubu.component';
import { MalzemeGrubuService } from 'app/entities/malzeme-grubu/malzeme-grubu.service';
import { MalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

describe('Component Tests', () => {
    describe('MalzemeGrubu Management Component', () => {
        let comp: MalzemeGrubuComponent;
        let fixture: ComponentFixture<MalzemeGrubuComponent>;
        let service: MalzemeGrubuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeGrubuComponent],
                providers: []
            })
                .overrideTemplate(MalzemeGrubuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeGrubuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeGrubuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MalzemeGrubu(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.malzemeGrubus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
