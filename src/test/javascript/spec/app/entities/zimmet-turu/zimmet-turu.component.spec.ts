/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { ZimmetTuruComponent } from 'app/entities/zimmet-turu/zimmet-turu.component';
import { ZimmetTuruService } from 'app/entities/zimmet-turu/zimmet-turu.service';
import { ZimmetTuru } from 'app/shared/model/zimmet-turu.model';

describe('Component Tests', () => {
    describe('ZimmetTuru Management Component', () => {
        let comp: ZimmetTuruComponent;
        let fixture: ComponentFixture<ZimmetTuruComponent>;
        let service: ZimmetTuruService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [ZimmetTuruComponent],
                providers: []
            })
                .overrideTemplate(ZimmetTuruComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZimmetTuruComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZimmetTuruService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ZimmetTuru(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.zimmetTurus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
