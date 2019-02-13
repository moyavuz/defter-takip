/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeComponent } from 'app/entities/malzeme/malzeme.component';
import { MalzemeService } from 'app/entities/malzeme/malzeme.service';
import { Malzeme } from 'app/shared/model/malzeme.model';

describe('Component Tests', () => {
    describe('Malzeme Management Component', () => {
        let comp: MalzemeComponent;
        let fixture: ComponentFixture<MalzemeComponent>;
        let service: MalzemeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeComponent],
                providers: []
            })
                .overrideTemplate(MalzemeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MalzemeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MalzemeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Malzeme(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.malzemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
